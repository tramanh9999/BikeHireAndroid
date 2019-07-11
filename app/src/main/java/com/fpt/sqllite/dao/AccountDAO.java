package com.fpt.sqllite.dao;

import androidx.room.Dao;
import androidx.room.Ignore;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.fpt.model.Account;

@Dao
public interface AccountDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Account account);

    @Query("SELECT * FROM account WHERE email = :email")
    Account findByEmail(String email);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Account account);
}
