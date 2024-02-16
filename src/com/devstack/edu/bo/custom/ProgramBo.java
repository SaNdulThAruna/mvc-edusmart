package com.devstack.edu.bo.custom;

import com.devstack.edu.bo.SuperBo;
import com.devstack.edu.dto.ProgramDto;
import com.devstack.edu.dto.StudentDto;

import java.sql.SQLException;
import java.util.List;

public interface ProgramBo extends SuperBo {
    public List<ProgramDto> loadAppPrograms() throws SQLException, ClassNotFoundException;

}
