package ma.prodenta.service.modules.dashboard.impl;

import ma.prodenta.common.exceptions.ArgumentException;
import ma.prodenta.common.exceptions.ErreurLectureException;
import ma.prodenta.common.exceptions.ErreurSuppressionException;
import ma.prodenta.config.Application_contexte;
import ma.prodenta.entities.En.Dashboard;
import ma.prodenta.repository.modules.Dashboard.implementation.DashboardRepositoryImpl;
import ma.prodenta.service.modules.dashboard.api.DashboardService;
import java.util.Date;
import java.util.List;

public class DashboardServiceImpl implements DashboardService {

    @Override
    public Dashboard findById(Integer id) throws Exception {
        if (id == null || id <= 0) {
            throw new ArgumentException("l'id du dashboard est invalide");
        }
        DashboardRepositoryImpl dashboardRepo = Application_contexte.getDashboardRepository();
        Dashboard dashboard = dashboardRepo.findById(id);
        if (dashboard == null) {
            throw new ErreurLectureException("aucun dashboard n'existe avec cette id");
        }
        return dashboard;
    }

    @Override
    public List<Dashboard> findAll() throws Exception {
        DashboardRepositoryImpl dashboardRepo = Application_contexte.getDashboardRepository();
        List<Dashboard> dashboards = dashboardRepo.findAll();
        if (dashboards == null || dashboards.isEmpty()) {
            throw new ErreurLectureException("aucun dashboard n'existe dans la base");
        }
        return dashboards;
    }

    @Override
    public Dashboard findByPeriode(Date dateDebut, Date dateFin) throws Exception {
        if (dateDebut == null || dateFin == null) {
            throw new ArgumentException("les dates fournies sont invalides");
        }
        if (dateDebut.after(dateFin)) {
            throw new ArgumentException("la date de debut ne peut pas etre apres la date de fin");
        }
        DashboardRepositoryImpl dashboardRepo = Application_contexte.getDashboardRepository();
        Dashboard dashboard = dashboardRepo.findByPeriode(dateDebut, dateFin);
        if (dashboard == null) {
            throw new ErreurLectureException("aucun dashboard n'existe pour cette periode");
        }
        return dashboard;
    }

    @Override
    public Dashboard save(Dashboard dashboard) throws Exception {
        if (dashboard == null) {
            throw new ArgumentException("le dashboard fourni est null");
        }
        if (dashboard.getDateDebut() == null || dashboard.getDateFin() == null) {
            throw new ArgumentException("les dates du dashboard sont invalides");
        }
        if (dashboard.getNbPatients() < 0 || dashboard.getNbActes() < 0) {
            throw new ArgumentException("les valeurs numeriques sont invalides");
        }
        if (dashboard.getTotalRecettes() < 0 || dashboard.getTotalDepenses() < 0) {
            throw new ArgumentException("les montants financiers ne peuvent pas etre negatifs");
        }
        DashboardRepositoryImpl dashboardRepo = Application_contexte.getDashboardRepository();
        return dashboardRepo.save(dashboard);
    }

    @Override
    public Dashboard update(Dashboard dashboard) throws Exception {
        if (dashboard == null) {
            throw new ArgumentException("le dashboard fourni est null");
        }
        if (dashboard.getIdDashboard() == null || dashboard.getIdDashboard() <= 0) {
            throw new ArgumentException("l'id du dashboard est invalide");
        }
        if (dashboard.getDateDebut() == null || dashboard.getDateFin() == null) {
            throw new ArgumentException("les dates du dashboard sont invalides");
        }
        if (dashboard.getNbPatients() < 0 || dashboard.getNbActes() < 0) {
            throw new ArgumentException("les valeurs numeriques sont invalides");
        }
        DashboardRepositoryImpl dashboardRepo = Application_contexte.getDashboardRepository();
        return dashboardRepo.update(dashboard);
    }

    @Override
    public void delete(Integer id) throws Exception {
        if (id == null || id <= 0) {
            throw new ArgumentException("l'id du dashboard est invalide");
        }
        DashboardRepositoryImpl dashboardRepo = Application_contexte.getDashboardRepository();
        Dashboard existingDashboard = dashboardRepo.findById(id);
        if (existingDashboard == null) {
            throw new ErreurLectureException("impossible de supprimer, aucun dashboard avec cette id");
        }
        dashboardRepo.delete(id);
    }
}
