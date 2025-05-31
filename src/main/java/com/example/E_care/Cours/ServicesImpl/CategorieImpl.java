package com.example.E_care.Cours.ServicesImpl;

import com.example.E_care.Cours.dao.CategorieDao;
import com.example.E_care.Cours.models.Categorie;
import com.example.E_care.Cours.Services.CategorieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

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
    public Categorie findCategorie(Long id) {
        return this.categorieDao.findById(id).get();
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
    public Categorie update(Long id, String titre, String description, MultipartFile image){
        Optional<Categorie> optionalCategorie = categorieDao.findById(id);
        if (optionalCategorie.isPresent()) {
            Categorie categorie = optionalCategorie.get();
            categorie.setTitre(titre);
            categorie.setDescription(description);

            // Vérifie si une nouvelle image est envoyée
            if (image != null && !image.isEmpty()) {
                try {
                    categorie.setImage(image.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return categorieDao.save(categorie);
        }
        else {
            return null;
    }
}

    @Override
    public Long getTotalCategorie(){
        return this.categorieDao.count();
    }


}