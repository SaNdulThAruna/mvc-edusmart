package com.devstack.edu.bo.custom.impl;

import com.devstack.edu.bo.custom.TrainerBo;
import com.devstack.edu.dao.DaoFactory;
import com.devstack.edu.dao.custom.TrainerDao;

import java.sql.SQLException;
import java.util.List;

public class TrainerBoImpl implements TrainerBo {

    TrainerDao trainerDao = DaoFactory.getDao(DaoFactory.DaoType.TRAINER);
    @Override
    public List<String> loadAllTrainers() throws SQLException, ClassNotFoundException {
        return trainerDao.loadAllTrainers();
    }
}
