package com.example.E_care.Cours.ServicesImpl;

import com.example.E_care.Cours.dao.CategorieDao;
import com.example.E_care.Cours.models.Categorie;
import com.example.E_care.Cours.Services.CategorieService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "categorieService")
public class CategorieImpl implements CategorieService {
    @Autowired
    private CategorieDao categorieDao;

    @Override
    public List<Categorie> findAll() {
        return this.categorieDao.findAll();
    }

    @Override
    public Categorie save(Categorie categorie) {
        return this.categorieDao.save(categorie);
    }


    @Override
    public Boolean deleteById(Long id)  {
        Boolean resultat = false;
        try {
            this.categorieDao.deleteById(id);
            resultat = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultat;
    }

    @Override
    public Categorie update(Long id, Categorie categorie)  {
        Categorie CategorieExecting = this.categorieDao.findById(id).orElseThrow(()->new RuntimeException("Classe not found"));
        BeanUtils.copyProperties(categorie, CategorieExecting);
        return
                this.categorieDao.save(CategorieExecting);
    }


}