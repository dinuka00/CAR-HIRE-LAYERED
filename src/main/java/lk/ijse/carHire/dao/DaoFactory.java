package lk.ijse.carHire.dao;

import lk.ijse.carHire.dao.custom.impl.CategoryDaoImpl;

public class DaoFactory {
    public static  <T>T getDao(DaoType type){
        switch (type){
            case CATEGORY :
                return (T) new CategoryDaoImpl();

            default :
                return  null;
        }

    }
}
