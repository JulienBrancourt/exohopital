package org.example.exohopital.repository;

import org.example.exohopital.models.Patient;
import org.hibernate.Session;

import java.util.List;

public class PatientRepository extends BaseRepository<Object> {
    public PatientRepository(Session session) {

        super(session);
    }

    @Override
    public Patient findById(int id) {
        return (Patient) _session.get(Patient.class, id);
    }

    @Override
    public List<Object> findAll() {
        return _session.createQuery("from Patient").list();
    }
}
