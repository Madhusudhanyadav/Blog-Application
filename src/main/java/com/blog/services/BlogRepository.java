package com.blog.services;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blog.entities.Blog;

public interface BlogRepository extends JpaRepository<Blog,Integer>{
	
	@Query("Select b from Blog b where b.user.id = :userId")
	public List<Blog> getBlogsByUserId(@Param("userId") int userid);
}
