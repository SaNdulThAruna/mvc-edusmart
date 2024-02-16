package com.devstack.edu.dao.custom;

import com.devstack.edu.dao.CrudDao;
import com.devstack.edu.entity.Trainer;

import java.sql.SQLException;
import java.util.List;

public interface TrainerDao extends CrudDao<Trainer,Long> {
    public boolean updateTrainer(Trainer trainer, long selectedTrainerId) throws SQLException, ClassNotFoundException;
    public List<Trainer> findAllTrainers(String searchText) throws SQLException, ClassNotFoundException;
    public List<String> loadAllTrainers() throws SQLException, ClassNotFoundException;
}
