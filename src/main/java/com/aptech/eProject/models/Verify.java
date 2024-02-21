/*
package com.aptech.eProject.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.sql.Timestamp;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "tbl_verifications")
@SQLDelete(sql = "UPDATE tbl_verifications SET deleted_at = CURRENT_TIMESTAMP, modified_at = CURRENT_TIMESTAMP WHERE id = ?")
@Where(clause = "deleted_at is null")
public class Verify extends Model {
	@Column(name = "token")
	private String code;

	@Column(name = "expired_at")
	private Timestamp expiredAt;

	@Column(name = "user_id")
	private Timestamp userId;
}
*/
