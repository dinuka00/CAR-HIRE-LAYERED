package lk.ijse.carHire.dao;

import lk.ijse.carHire.dao.custom.CustomerDao;
import lk.ijse.carHire.dao.custom.impl.*;

public class DaoFactory {
    public static  <T>T getDao(DaoType type){
        switch (type){
            case CATEGORY :
                return (T) new CategoryDaoImpl();

            case  CAR :
                return  (T) new CarDaoImpl();

            case  CUSTOMER:
                return  (T) new CustomerDaoImpl();
            case  RENT:
                return  (T) new RentDaoImpl();
            case  RETURN:
                return  (T) new ReturnDaoImpl();
            default :
                return  null;
        }

    }
}
