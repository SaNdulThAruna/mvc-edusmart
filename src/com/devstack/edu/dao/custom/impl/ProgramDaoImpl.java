package com.devstack.edu.dao.custom.impl;

import com.devstack.edu.dao.CrudUtil;
import com.devstack.edu.dao.custom.ProgramDao;
import com.devstack.edu.entity.Program;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProgramDaoImpl implements ProgramDao {
    @Override
    public boolean save(Program program) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(Program program) {
        return false;
    }

    @Override
    public boolean delete(Long aLong) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Program find(Long aLong) {
        return null;
    }

    @Override
    public List<Program> findAll() throws SQLException, ClassNotFoundException {

        List<Program> list = new ArrayList<>();
        ResultSet set = CrudUtil.execute("SELECT p.program_id, p.program_name, p.hours, p.amount, p.trainer_trainer_id, p.user_email," +
                "GROUP_CONCAT(pc.header) AS content FROM program p " +
                "INNER JOIN program_content pc ON p.program_id = pc.program_program_id GROUP BY p.program_id");
        while (set.next()){
            list.add(
                    new Program(set.getLong(1),set.getInt(3),set.getString(2),set.getDouble(4),
                            set.getString(6),set.getLong(5),Arrays.asList(set.getString(7).split(",")))
            );
        }
        return list;
    }
}
