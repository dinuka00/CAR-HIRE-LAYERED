package lk.ijse.carHire.business;

import lk.ijse.carHire.business.custom.impl.CategoryBoImpl;

public class BoFactory {
    public static <T>T getBo(BoType type){

        switch (type){
            case CATEGORY :
                return (T) new CategoryBoImpl();
            default:
                return null;
        }

    }
}
