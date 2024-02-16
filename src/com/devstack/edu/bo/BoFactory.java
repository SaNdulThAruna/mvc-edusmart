package com.devstack.edu.bo;

import com.devstack.edu.bo.custom.TrainerBo;
import com.devstack.edu.bo.custom.impl.StudentBoImpl;
import com.devstack.edu.bo.custom.impl.TrainerBoImpl;
import com.devstack.edu.dao.custom.impl.StudentDaoImpl;
import com.devstack.edu.dao.custom.impl.TrainerDaoImpl;

public class BoFactory {
    private BoFactory daoFactory;
    private BoFactory(){}

    public enum BoType{
        STUDENT,TRAINER
    }

    public static <T> T getBo(BoType type){
        switch(type){
            case STUDENT : return (T)new StudentBoImpl();
            case TRAINER: return  (T)new TrainerBoImpl();
            default:
                return null;
        }
    }
}
