package com.devstack.edu.dao.custom.impl;

import com.devstack.edu.dao.CrudUtil;
import com.devstack.edu.dao.custom.TrainerDao;
import com.devstack.edu.entity.Trainer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TrainerDaoImpl implements TrainerDao {
    @Override
    public boolean updateTrainer(Trainer trainer, long selectedTrainerId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE trainer set trainer_name=?,trainer_email=?,nic=?,address=? " +
                        "WHERE trainer_id=?", trainer.getTrainerName(), trainer.getEmail(), trainer.getNic(),
                trainer.getAddress(), selectedTrainerId
        );
    }

    @Override
    public List<Trainer> findAllTrainers(String searchText) throws SQLException, ClassNotFoundException {
        ArrayList<Trainer> trainers;
        try (ResultSet rs = CrudUtil.execute("SELECT * FROM trainer WHERE trainer.trainer_name LIKE ? OR trainer.trainer_email LIKE ?", searchText, searchText)) {
            trainers = new ArrayList<>();
            while (rs.next()) {
                trainers.add(
                        new Trainer(rs.getLong(1), rs.getString(2), rs.getString(3),
                                rs.getString(4), rs.getString(5), rs.getBoolean(6))
                );
            }
        }

        return trainers;
    }

    @Override
    public List<String> loadAllTrainers() throws SQLException, ClassNotFoundException {

        ResultSet rs = CrudUtil.execute("SELECT * FROM trainer");
        ArrayList<String> trainers = new ArrayList<>();
        while (rs.next()) {
            trainers.add(rs.getInt(1)+"->"+rs.getString(2));
        }
         return trainers;
    }

    @Override
    public boolean save(Trainer trainer) throws SQLException, ClassNotFoundException {

        return CrudUtil.execute("INSERT INTO trainer(trainer_name, trainer_email, nic, address, trainer_status) " + "VALUES (?,?,?,?,?)",
                trainer.getTrainerName(), trainer.getEmail(), trainer.getEmail(), trainer.getNic(), trainer.getAddress(), trainer.isStatus()
        );
    }

    @Override
    public boolean update(Trainer trainer) {
        return false;
    }

    @Override
    public boolean delete(Long aLong) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Trainer find(Long aLong) {
        return null;
    }

    @Override
    public List<Trainer> findAll() {
        return null;
    }
}
