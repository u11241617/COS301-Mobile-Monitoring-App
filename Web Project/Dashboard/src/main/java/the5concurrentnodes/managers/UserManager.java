package the5concurrentnodes.managers;


import the5concurrentnodes.entities.AccessLevel;
import the5concurrentnodes.entities.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Stateless
public class UserManager{

    @PersistenceContext
    private EntityManager em;

    /**
     * Store new user details on database
     * @param email  user email
     * @param password user password
     */
    public User persist(String email, String password,AccessLevel ac) {

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setAccessleveltbByAccessLevelId(ac);

        em.persist(user);

        return getUserByEmail(email);
    }

    /**
     *
     * @param email email of user
     * @return true if user with specified email exist else false
     */
    public boolean userExist(String email) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);

        Root<User> userRoot = query.from(User.class);
        query.where(cb.equal(userRoot.get("email"), email));

        User user = null;

        try {
            user = em.createQuery(query).getSingleResult();
        }catch(NoResultException e){}

        return user!= null;
    }

    /**
     *
     * @return return all users in database
     */
    public List<User> getAllUser() {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);

        Root<User> userRoot = query.from(User.class);
        query.select(userRoot);

        return em.createQuery(query).getResultList();
    }

    public User getUserByEmail(String email) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);

        Root<User> userRoot = query.from(User.class);
        query.where(cb.equal(userRoot.get("email"), email));

        User user = null;

        try {
            user = em.createQuery(query).getSingleResult();
        }catch(NoResultException e){}

        return user;
    }


    public User getUserById(int id) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);

        Root<User> userRoot = query.from(User.class);
        query.where(cb.equal(userRoot.get("userId"), id));

        User user = null;

        try {
            user = em.createQuery(query).getSingleResult();
        }catch(NoResultException e){}

        return user;
    }

    public int login(String email, String password) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);

        Root<User> userRoot = query.from(User.class);
        query.where(cb.equal(userRoot.get("email"), email));

        int id = -1;
        try {
            User user = em.createQuery(query).getSingleResult();

            if(user != null) {

                if(user.getPassword().equals(password)) {

                    id = user.getUserId();
                }
            }
        }catch(NoResultException e){}

        return id;
    }

}
