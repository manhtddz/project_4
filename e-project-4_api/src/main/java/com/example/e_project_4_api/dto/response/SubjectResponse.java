/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.e_project_4_api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubjectResponse {
        private Integer id;
        private String title;
        private String image;
        private String description;
        private Boolean isDeleted;
        private Date createdAt;
        private Date modifiedAt;

//        public SubjectResponse(Integer id, String title, String image, String description, Boolean isDeleted, Date createdAt, Date modifiedAt) {
//                this.id = id;
//                this.title = title;
//                this.image = image;
//                this.description = description;
//                this.isDeleted = isDeleted;
//                this.createdAt = createdAt;
//                this.modifiedAt = modifiedAt;
//        }
//
//        public SubjectResponse() {
//        }
//
//        public Integer getId() {
//
//                return id;
//        }
//
//        public void setId(Integer id) {
//                this.id = id;
//        }
//
//        public String getTitle() {
//                return title;
//        }
//
//        public void setTitle(String title) {
//                this.title = title;
//        }
//
//        public String getImage() {
//                return image;
//        }
//
//        public void setImage(String image) {
//                this.image = image;
//        }
//
//        public String getDescription() {
//                return description;
//        }
//
//        public void setDescription(String description) {
//                this.description = description;
//        }
//
//        public Boolean getDeleted() {
//                return isDeleted;
//        }
//
//        public void setDeleted(Boolean deleted) {
//                isDeleted = deleted;
//        }
//
//        public Date getCreatedAt() {
//                return createdAt;
//        }
//
//        public void setCreatedAt(Date createdAt) {
//                this.createdAt = createdAt;
//        }
//
//        public Date getModifiedAt() {
//                return modifiedAt;
//        }
//
//        public void setModifiedAt(Date modifiedAt) {
//                this.modifiedAt = modifiedAt;
//        }
}
