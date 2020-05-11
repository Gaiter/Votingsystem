package com.example.votingsystem.service.user;

import com.example.votingsystem.AuthorizedUser;
import com.example.votingsystem.model.User;
import com.example.votingsystem.repository.UserRepository;
import com.example.votingsystem.repository.VoiceRepository;
import com.example.votingsystem.to.UserTo;
import com.example.votingsystem.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

import static com.example.votingsystem.util.Util.prepareUserToSave;
import static com.example.votingsystem.util.Util.updateUserFromTo;
import static com.example.votingsystem.util.ValidationUtil.checkNotFound;
import static com.example.votingsystem.util.ValidationUtil.checkNotFoundWithId;
import static org.springframework.data.domain.Sort.by;

@Service("userService")
public class UserServiceImpl implements UserService, UserDetailsService {
    private static final Sort SORT_NAME_EMAIL = by(Sort.Direction.ASC, "name", "email");

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository repository, PasswordEncoder passwordEncoder, VoiceRepository voiceRepository) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User create(User user) {
        Assert.notNull(user, "user must not be null");
        return repository.save(prepareUserToSave(user, passwordEncoder));
    }

    @Override
    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id), id);
    }

    @Override
    public User get(int id) throws NotFoundException {
        return checkNotFoundWithId(repository.findById(id).orElse(null), id);
    }

    @Override
    public User getByEmail(String email) throws NotFoundException {
        Assert.notNull(email, "email must not be null");
        return checkNotFound(repository.getByEmail(email), "email=" + email);
    }

    @Override
    public List<User> getAll() {
        return repository.findAll(SORT_NAME_EMAIL);
    }

    @Override
    public void update(User user) {
        Assert.notNull(user, "user must not be null");
        checkNotFoundWithId(repository.save(prepareUserToSave(user, passwordEncoder)), user.getId());
    }

    @Transactional
    @Override
    public void update(UserTo userTo) {
        User user = updateUserFromTo(get(userTo.getId()), userTo);
        repository.save(prepareUserToSave(user, passwordEncoder));
    }

    @Override
    public AuthorizedUser loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.getByEmail(email.trim().toLowerCase());
        if (user == null) {
            throw new UsernameNotFoundException("User " + email + " is not found");
        }
        return new AuthorizedUser(user);
    }
}