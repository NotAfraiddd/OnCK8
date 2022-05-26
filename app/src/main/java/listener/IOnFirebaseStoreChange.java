package listener;

import entity.UserHappy;

public interface IOnFirebaseStoreChange {
    void writeToFirestore(UserHappy userHappy);
}
