package lk.ijse.carHire.dao.custom.impl;

import javafx.scene.control.Alert;
import lk.ijse.carHire.dao.custom.CategoryDao;
import lk.ijse.carHire.db.DBConnection;
import lk.ijse.carHire.entity.CarCategories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoryDaoImpl implements CategoryDao {

    @Override
    public boolean save(CarCategories entity) throws Exception {
        Connection con = DBConnection.getInstance().getConnection();

        String sql = "INSERT INTO carcategories VALUES(?,?)";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, entity.getCategoryID());
        pstm.setString(2, entity.getCategoryName());



        return pstm.executeUpdate() > 0;
    }

    @Override
    public boolean update(CarCategories entity) throws Exception {

        Connection con = DBConnection.getInstance().getConnection();

        String sql = "UPDATE carcategories SET CategoryName=? WHERE CategoryID=?";

        PreparedStatement pstm = con.prepareStatement(sql);

        pstm.setString(1, entity.getCategoryName());
        pstm.setString(2, entity.getCategoryID());

        return pstm.executeUpdate() > 0;
    }

    @Override
    public CarCategories search(String s) throws Exception {

        Connection con = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM carcategories WHERE CategoryID=?";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1,s);

        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()){
            CarCategories cce = new CarCategories(
                    resultSet.getString(1),
                    resultSet.getString(2)
            );

            return cce;
        }else {
            new Alert(Alert.AlertType.WARNING,"Category Not Found").show();
        }

        return null;



    }

    @Override
    public boolean delete(String s) throws Exception {

        Connection con = DBConnection.getInstance().getConnection();

        String sql = "DELETE FROM carcategories WHERE CategoryID=?";
        PreparedStatement pstm = con.prepareStatement(sql);

        pstm.setString(1,s);

        return pstm.executeUpdate() >0;

    }


    @Override
    public List<CarCategories> getAll() throws Exception {

        List<CarCategories> carCategories = new ArrayList<>();

        Connection con = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM carcategories";

        PreparedStatement pstm = con.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()){
            carCategories.add(new CarCategories(
                    resultSet.getString(1),
                    resultSet.getString(2)
            ));
        }

        return carCategories;
    }
}
