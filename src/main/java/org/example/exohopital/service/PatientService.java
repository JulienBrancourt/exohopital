package org.example.exohopital.service;

import org.example.exohopital.models.Patient;
import org.example.exohopital.repository.PatientRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.LocalDate;
import java.util.List;

public class PatientService {
    private PatientRepository patientRepository;
    private SessionFactory _sessionFactory;
    private Session session;

    public PatientService(SessionFactory sessionFactory) {
        _sessionFactory = sessionFactory;
    }

    public boolean createPatient(String lastname, String firstname, LocalDate birthDate, String s) {
        boolean result = false;
        session = _sessionFactory.openSession();
        session.beginTransaction();
        patientRepository = new PatientRepository(session);
        Patient patient = new Patient(lastname, firstname, birthDate);
        try {
            patientRepository.create(patient);
            session.getTransaction().commit();
            result = true;
        } catch (Exception e) {
            try {
                session.getTransaction().rollback();
            }catch (Exception e1) {
                System.out.println(e1.getMessage());
            }
        }finally {
            session.close();
        }
        return result;
    }

    public Patient getPatient(int id) {
        Patient patient = null;
        session = _sessionFactory.openSession();
        patientRepository = new PatientRepository(session);
        try {
            patient = (Patient) patientRepository.findById(id);
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            session.close();
        }return patient;
    }

    public List<Object> getPatients() {
        List<Object> patients = null;
        session = _sessionFactory.openSession();
        patientRepository = new PatientRepository(session);
        try {
            patients = patientRepository.findAll();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            session.close();
        } return patients;
    }
}
