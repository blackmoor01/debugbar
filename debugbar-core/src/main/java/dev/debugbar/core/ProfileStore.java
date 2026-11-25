package dev.debugbar.core;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Optional;

/**
 * Bounded in-memory store of the most recent request profiles,
 * so memory use stays flat no matter how long the app runs.
 * 
 * @author klvna
 */

public class ProfileStore {

    private final int capacity;
    private final Deque<RequestProfile> profiles;

    public ProfileStore(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("Capacity must be at least 1");
        }
        this.capacity = capacity;
        this.profiles = new ArrayDeque<>(capacity);
    }


    public synchronized void add(RequestProfile profile) {
        if (profiles.size() >= capacity) {
            profiles.removelast(); // drop the oldest profile to make room for the new one
        }
        profiles.addFirst(profile); // newest profile is at the front
    }

    public synchronized List<RequestProfile> recent() {
        return new ArrayList<>(profiles);
    }

    public synchronized Optional<RequestProfile> find(String id) {
        return profiles.stream()
                .filter(profile -> profile.getId().equals(id))
                .findFirst();
    }

    public synchronized void clear() {
        profiles.clear();
    }

}
