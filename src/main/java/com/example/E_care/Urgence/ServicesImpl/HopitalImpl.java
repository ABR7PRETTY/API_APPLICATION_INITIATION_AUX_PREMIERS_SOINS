package com.example.E_care.Urgence.ServicesImpl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.E_care.Urgence.Services.HopitalService;
import com.example.E_care.Urgence.dao.HopitalDao;
import com.example.E_care.Urgence.models.Hopital;

@Service (value = "hopitalService")
public class HopitalImpl implements HopitalService {

    @Autowired
    private HopitalDao hopitalDao;

    @Override
    public List<Hopital> findAll() {

        return this.hopitalDao.findAll();
    }

    @Override
    public Hopital save(Hopital hopital) {
        return this.hopitalDao.save(hopital);
    }

    @Override
    public Boolean deleteById(Long id) {
        if(this.hopitalDao.existsById(id)){
            this.hopitalDao.deleteById(id);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Hopital update(Long id, Hopital hopital) {
        Hopital HopitalExistant = this.hopitalDao.findById(id).orElseThrow(() -> new RuntimeException("Hopital not found"));
        BeanUtils.copyProperties(hopital, HopitalExistant);
        return this.hopitalDao.save(HopitalExistant);
    }
    
}
