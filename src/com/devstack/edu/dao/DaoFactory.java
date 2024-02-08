package com.devstack.edu.dao;

import com.devstack.edu.dao.custom.impl.StudentDaoImpl;
import com.devstack.edu.dao.custom.impl.TrainerDaoImpl;

public class DaoFactory {
    private DaoFactory daoFactory;
    private DaoFactory(){}

    public enum DaoType{
        STUDENT,TRAINER
    }

    public static <T> T getDao(DaoType type){
        switch(type){
            case STUDENT : return (T)new StudentDaoImpl();
            case TRAINER: return  (T)new TrainerDaoImpl();
            default:
                return null;
        }
    }
}
