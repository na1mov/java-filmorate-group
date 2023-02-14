package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User add(User user) {
        return userStorage.add(user);
    }

    public User update(User user) {
        return userStorage.update(user);
    }

    public User delete(User user) {
        return userStorage.delete(user);
    }

    public ArrayList<User> findAll() {
        return userStorage.findAll();
    }

    public User findById(Long id) {
        return userStorage.findById(id);
    }

    public User addFriend(Long userId, Long friendId) {
        return userStorage.addFriend(userId, friendId);
    }

    public User removeFriend(Long userId, Long friendId) {
        return userStorage.removeFriend(userId, friendId);
    }

    public ArrayList<User> getCommonFriends(Long userId, Long friendId) {
        HashSet<Long> userFriends = (HashSet<Long>) userStorage.getFriendsIds(userId);
        HashSet<Long> friendFriends = (HashSet<Long>) userStorage.getFriendsIds(friendId);
        HashSet<Long> commonFriends = new HashSet<>();

        for (Long userFriendId : userFriends) {
            if (friendFriends.contains(userFriendId)) {
                commonFriends.add(userFriendId);
            }
        }
        return (ArrayList<User>) userStorage.findAll().stream()
                .filter(v -> commonFriends.contains(v.getId()))
                .collect(Collectors.toList());
    }

    public ArrayList<User> getFriends(Long id) {
        Set<Long> friendsSet = userStorage.findById(id).getSetFriends();
        return (ArrayList<User>) userStorage.findAll().stream()
                .filter(v -> friendsSet.contains(v.getId()))
                .collect(Collectors.toList());
    }
}
