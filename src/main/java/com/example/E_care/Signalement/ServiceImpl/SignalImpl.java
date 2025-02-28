package com.example.E_care.Signalement.ServiceImpl;

import com.example.E_care.Signalement.Dao.SignalDao;
import com.example.E_care.Signalement.Service.SignalService;
import com.example.E_care.Signalement.models.Signal;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "signalService")
public class SignalImpl implements SignalService {

    @Autowired
    private SignalDao signalDao;


    @Override
    public Signal save(Signal signal) {
        return this.signalDao.save(signal);
    }

    @Override
    public Signal update(Long id, Signal signal) {
        Signal signalToUpdate = this.signalDao.findById(id).orElseThrow(() -> new RuntimeException("Signal introuvable"));
        BeanUtils.copyProperties(signal, signalToUpdate);
        return this.signalDao.save(signalToUpdate);
    }

    @Override
    public Boolean deleteById(Long id) {
        Boolean result = false;
        if (this.signalDao.existsById(id)) {
            this.signalDao.deleteById(id);
            result = true;
        }
        return result;
    }

    @Override
    public List<Signal> findAll() {
        return this.signalDao.findAll();
    }
    
}
