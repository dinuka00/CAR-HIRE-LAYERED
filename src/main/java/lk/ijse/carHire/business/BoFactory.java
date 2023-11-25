package lk.ijse.carHire.business;

import lk.ijse.carHire.business.custom.impl.*;
import lk.ijse.carHire.dao.custom.impl.CustomerDaoImpl;

public class BoFactory {
    public static <T>T getBo(BoType type){

        switch (type){
            case CATEGORY :
                return (T) new CategoryBoImpl();

            case  CAR :
                    return (T) new CarBoImpl();

            case  CUSTOMER :
                return (T) new CustomerBoImpl();
            case RENT:
                return  (T) new RentBoImpl();
            case RETURN:
                return  (T) new ReturnBoImpl();
            default:
                return null;
        }

    }
}
