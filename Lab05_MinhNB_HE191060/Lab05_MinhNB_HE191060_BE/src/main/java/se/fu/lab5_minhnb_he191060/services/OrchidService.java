package se.fu.lab5_minhnb_he191060.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.fu.lab5_minhnb_he191060.entities.Orchid;
import se.fu.lab5_minhnb_he191060.repositories.IOrchidRepository;

import java.util.List;
import java.util.Optional;

@Service
public class OrchidService implements IOrchidService {

    @Autowired
    private IOrchidRepository iOrchidRepository;

    @Override
    public List<Orchid> getAllOrchids() {
        return iOrchidRepository.findAll();
    }

    @Override
    public Orchid insertOrchid(Orchid orchid) {
        return iOrchidRepository.save(orchid);
    }

    @Override
    public Orchid updateOrchid(int orchidId, Orchid orchid) {
        Orchid o = iOrchidRepository.findById(orchidId).orElse(null);
        if (o != null) {
            o.setOrchidName(orchid.getOrchidName());
            o.setOrchidDescription(orchid.getOrchidDescription());
            o.setOrchidCategory(orchid.getOrchidCategory());
            o.setNatural(orchid.isNatural());
            o.setAttractive(orchid.isAttractive());
            o.setOrchidUrl(orchid.getOrchidUrl());
            return iOrchidRepository.save(o);
        }
        return null;
    }

    @Override
    public void deleteOrchid(int orchidId) {
        iOrchidRepository.deleteById(orchidId);
    }

    @Override
    public Optional<Orchid> getOrchidById(int orchidId) {
        return iOrchidRepository.findById(orchidId);
    }
}
