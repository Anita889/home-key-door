package com.example.homekeydoor.dataservices;

import com.example.homekeydoor.consts.KeyStatus;
import com.example.homekeydoor.entities.Home;
import com.example.homekeydoor.entities.HomeUser;
import com.example.homekeydoor.entities.Key;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import com.example.homekeydoor.repositories.KeyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KeyDataService {

    @Autowired
    private KeyRepository keyRepository;

    public Key createKey(Home home, String code) {
        Key key = new Key();
        key.setCode(code);
        key.setStatus(KeyStatus.AVAILABLE);
        key.setHome(home);
        key.setHomeUser(null);
        return keyRepository.save(key);
    }

    public List<Key> getKeysByOwnerId(Long ownerId) {
        return keyRepository.findAllByOwnerId(ownerId);
    }

    public List<Key> getKeysByHomeUserId(Long homeUserId) {
        return keyRepository.findAllByHomeUserIdAndRemovedFalse(homeUserId);
    }

    public Key takeKey(Long keyId, HomeUser homeUser) {
        Key key = getKeyById(keyId);
        if (key.getStatus() != KeyStatus.AVAILABLE) {
            throw new IllegalStateException("Only available keys can be taken");
        }
        key.setStatus(KeyStatus.TAKEN);
        key.setHomeUser(homeUser);
        return keyRepository.save(key);
    }

    public Key returnKey(Long keyId, HomeUser homeUser) {
        Key key = getKeyById(keyId);
        validateKeyHolder(key, homeUser);
        if (key.getStatus() != KeyStatus.TAKEN) {
            throw new IllegalStateException("Only taken keys can be returned");
        }
        key.setStatus(KeyStatus.AVAILABLE);
        key.setHomeUser(null);
        return keyRepository.save(key);
    }

    public Key reportLost(Long keyId, HomeUser homeUser) {
        Key key = getKeyById(keyId);
        validateKeyHolder(key, homeUser);
        if (key.getStatus() != KeyStatus.TAKEN) {
            throw new IllegalStateException("Only taken keys can be reported as lost");
        }
        key.setStatus(KeyStatus.LOST);
        return keyRepository.save(key);
    }

    public Key getKeyById(Long keyId) {
        return keyRepository.findById(keyId)
                .filter(key -> !key.isRemoved())
                .orElseThrow(() -> new EntityNotFoundException("Key not found with id: " + keyId));
    }

    private void validateKeyHolder(Key key, HomeUser homeUser) {
        if (key.getHomeUser() == null || !key.getHomeUser().getId().equals(homeUser.getId())) {
            throw new IllegalStateException("Key is not assigned to this home user");
        }
    }
}
