package com.devstack.edu.bo.custom.impl;

import com.devstack.edu.bo.custom.ProgramBo;
import com.devstack.edu.dto.ProgramDto;

import java.sql.SQLException;
import java.util.List;

public class ProgramBoImpl implements ProgramBo {
    @Override
    public List<ProgramDto> loadAppPrograms() throws SQLException, ClassNotFoundException {
        return null;
    }
}
