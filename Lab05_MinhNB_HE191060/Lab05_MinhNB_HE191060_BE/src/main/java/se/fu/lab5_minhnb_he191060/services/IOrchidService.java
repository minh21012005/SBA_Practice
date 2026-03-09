package se.fu.lab5_minhnb_he191060.services;

import se.fu.lab5_minhnb_he191060.entities.Orchid;

import java.util.List;
import java.util.Optional;

public interface IOrchidService {
    public List<Orchid> getAllOrchids();
    public Orchid insertOrchid(Orchid orchid);
    public Orchid updateOrchid(int orchidId, Orchid orchid);
    public void deleteOrchid(int orchidId);
    public Optional<Orchid> getOrchidById(int orchidId);

}
