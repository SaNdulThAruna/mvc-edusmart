package com.devstack.edu.bo.custom;

import java.sql.SQLException;
import java.util.List;

public interface TrainerBo {
    public List<String> loadAllTrainers() throws SQLException, ClassNotFoundException;
}
