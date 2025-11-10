package ma.prodenta.service.modules.patient.baseImplementation;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.prodenta.entities.En.Patient;
import ma.prodenta.mvc.dto.PatientDTO;
import ma.prodenta.repository.modules.patient.api.PatientDao;
import ma.prodenta.service.modules.patient.api.PatientService;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientServiceImpl implements PatientService {

    private PatientDao repository;

    private static String formatDate(LocalDateTime dt) {
        return dt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    private static int computeAge(LocalDate birthDate) {
        if (birthDate == null) return 0;
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    private static PatientDTO apply(Patient p) {
        LocalDate birth = Instant.ofEpochMilli(p.getDateNaissance().toEpochDay())
                .atZone(ZoneId.systemDefault()).toLocalDate();
// ici on n'a pas de date de création dans l'entité; on formate la date de naissance
        LocalDateTime displayDate = birth.atStartOfDay();
        return PatientDTO.builder()
                .nomComplet(p.getNom() == null ? "" : p.getNom().trim())
                .age(computeAge(birth))
                .dateCreationFormatee(formatDate(displayDate))
                .build();
    }

    @Override
    public List<PatientDTO> getTodayPatientsAsDTO() throws Exception {
        LocalDate today = LocalDate.now();
        return repository.findAll().stream()
                .filter(p -> p.getDateNaissance() != null) // simple guard
                .sorted(Comparator.comparing(Patient::getId).reversed())
                .map(PatientServiceImpl::apply)
                .collect(Collectors.toList());
    }
}
