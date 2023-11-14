package lk.ijse.carHire.business.custom.impl;

import lk.ijse.carHire.business.BoFactory;
import lk.ijse.carHire.business.BoType;
import lk.ijse.carHire.business.custom.CategoryBo;
import lk.ijse.carHire.dao.custom.CategoryDao;
import lk.ijse.carHire.dao.DaoFactory;
import lk.ijse.carHire.dao.DaoType;
import lk.ijse.carHire.dao.custom.impl.CategoryDaoImpl;
import lk.ijse.carHire.dto.CategoryDto;
import lk.ijse.carHire.entity.CarCategories;

import java.util.ArrayList;
import java.util.List;

public class CategoryBoImpl implements CategoryBo {

    CategoryDao dao = DaoFactory.getDao(DaoType.CATEGORY);



    @Override
    public boolean saveCategory(CategoryDto dto) throws Exception {

       var carCategories =  new CarCategories(
                dto.getId(),
                dto.getCategoryName()
        );

        return dao.save(carCategories);
    }

    @Override
    public boolean updateCategory(CategoryDto categoryDto) throws Exception {

        var carCategories = new CarCategories(
                categoryDto.getId(),
                categoryDto.getCategoryName());

        return dao.update(carCategories);
    }

    @Override
    public CategoryDto searchCategory(String id) throws Exception{
      CarCategories carCategories = dao.search(id);

      return   new CategoryDto(
              carCategories.getCategoryID(),
              carCategories.getCategoryName()
      );

    }

    @Override
    public boolean deleteCategory(String id) throws Exception {

        return dao.delete(id);

    }

    @Override
    public List<CategoryDto> getAllCategories() throws Exception {

        List<CarCategories> carCategoriesList = dao.getAll();

        List<CategoryDto> dtoList = new ArrayList<>();

        for(CarCategories carCategory : carCategoriesList){
            dtoList.add(new CategoryDto(carCategory.getCategoryID(),carCategory.getCategoryName()));

        }


        return dtoList;
    }


}
