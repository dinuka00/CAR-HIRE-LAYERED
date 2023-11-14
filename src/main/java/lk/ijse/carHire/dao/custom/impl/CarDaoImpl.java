package lk.ijse.carHire.dao.custom.impl;

import lk.ijse.carHire.dao.CrudDao;
import lk.ijse.carHire.dao.custom.CarDao;
import lk.ijse.carHire.entity.Car;

import java.util.List;

public class CarDaoImpl implements CarDao {
    @Override
    public boolean save(Car entity) throws Exception {
        return false;
    }

    @Override
    public boolean update(Car entity) throws Exception {
        return false;
    }

    @Override
    public Car search(String s) throws Exception {
        return null;
    }

    @Override
    public boolean delete(String s) throws Exception {
        return false;
    }



    @Override
    public List<Car> getAll() throws Exception {
        return null;
    }
}
