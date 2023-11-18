package lk.ijse.carHire.business;

import lk.ijse.carHire.business.custom.impl.CarBoImpl;
import lk.ijse.carHire.business.custom.impl.CategoryBoImpl;
import lk.ijse.carHire.business.custom.impl.CustomerBoImpl;

public class BoFactory {
    public static <T>T getBo(BoType type){

        switch (type){
            case CATEGORY :
                return (T) new CategoryBoImpl();

            case  CAR :
                    return (T) new CarBoImpl();

            case  CUSTOMER :
                return (T) new CustomerBoImpl();

            default:
                return null;
        }

    }
}
