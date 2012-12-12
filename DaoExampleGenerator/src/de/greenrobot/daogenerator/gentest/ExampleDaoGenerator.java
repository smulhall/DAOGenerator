/*
 * Copyright (C) 2011 Markus Junginger, greenrobot (http://greenrobot.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.greenrobot.daogenerator.gentest;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;

/**
 * Generates entities and DAOs for the example project DaoExample.
 * 
 * Run it as a Java application (not Android).
 * 
 * @author Markus
 */
public class ExampleDaoGenerator {

    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(3, "com.osgo.verifired.entity");

        addAssignment(schema);

        new DaoGenerator().generateAll(schema, "../DaoExample/src-gen");
    }

    private static void addAssignment(Schema schema) {
        Entity assignment = schema.addEntity("Assignment");
        assignment.addIdProperty();
        assignment.addStringProperty("assignment_id");
        assignment.addStringProperty("time_slot_start");
        assignment.addStringProperty("time_slot_end");
        assignment.addIntProperty("survey_id");
        assignment.addStringProperty("additional_comments");
        assignment.addStringProperty("key");
        
        Entity installation = schema.addEntity("Installation");
        installation.addIdProperty();
        installation.addStringProperty("firstname");
        installation.addStringProperty("lastname");
        installation.addStringProperty("address_1");
        installation.addStringProperty("address_2");
        installation.addStringProperty("city");
        installation.addStringProperty("country");
        installation.addStringProperty("postcode");
        installation.addStringProperty("gps_latitude");
        installation.addStringProperty("gps_longitude");
        installation.addStringProperty("account");
        installation.addStringProperty("appt_engineer");
        installation.addStringProperty("additional_comments");
        
        Property installationId = assignment.addLongProperty("installationId").getProperty();
        assignment.addToOne(installation, installationId);
        
        // survey generation
        
        Entity survey = schema.addEntity("Survey");
        survey.addIdProperty();
        survey.addStringProperty("survey_title");
        
        Entity photo = schema.addEntity("Photo");
        photo.addIdProperty();
        photo.addStringProperty("title");
        Property photoToSuvey = photo.addLongProperty("surveyId").getProperty();
        photo.addToOne(survey, photoToSuvey);
        
        ToMany surveyToPhotos = survey.addToMany(photo, photoToSuvey);
        surveyToPhotos.setName("photos");
        
        Entity group = schema.addEntity("Group");
        group.setTableName("GROUPDB");
        group.addIdProperty();
        group.addStringProperty("group_title");
        Property surveyId = group.addLongProperty("surveyId").getProperty();
        group.addToOne(survey, surveyId);
        
        ToMany surveyToGroups = survey.addToMany(group, surveyId);
        surveyToGroups.setName("groups");
        
        Entity question = schema.addEntity("Question");
        question.addIdProperty();
        question.addStringProperty("alert_type");
        question.addStringProperty("question");
        question.addStringProperty("answer_type");
        question.addBooleanProperty("multiple_choice");
        question.addBooleanProperty("required");
        question.addStringProperty("answer_options");
        question.addBooleanProperty("requires_photo");
        question.addBooleanProperty("send");
        question.addBooleanProperty("requires_comments");
        question.addStringProperty("answer");
        question.addStringProperty("comments");
        question.addStringProperty("image");
        question.addStringProperty("video");
//        question.addStringProperty("description_codes");
        Property groupId = question.addLongProperty("groupId").getProperty();
        question.addToOne(group, groupId);
        
        ToMany groupToQuestions = group.addToMany(question, groupId);
        groupToQuestions.setName("questions");
        
        Entity surveyResponse = schema.addEntity("SurveyResponse");
        surveyResponse.addIdProperty();
        surveyResponse.addStringProperty("alert_type");
        
        Entity groupDB = schema.addEntity("GroupDB");
        groupDB.addIdProperty();
        groupDB.addLongProperty("survey");
        groupDB.addStringProperty("title");
        
        Entity project = schema.addEntity("Project");
        project.addIdProperty();
        project.addStringProperty("project_id");
        project.addStringProperty("project_custom_id");
        project.addStringProperty("project_name");
        project.addBooleanProperty("project_location");
        project.addBooleanProperty("project_start");
        project.addStringProperty("project_end");
        project.addBooleanProperty("business_name");
        project.addBooleanProperty("activity_type_name");
        project.addStringProperty("users");
        project.addStringProperty("surveys");
        project.addStringProperty("survey_ids");
        project.addStringProperty("activity_type_id");
        project.addStringProperty("client_id");
        
        Entity completionCode = schema.addEntity("CompletionCode");
        completionCode.addIdProperty();
        completionCode.addLongProperty("surveyId");
        completionCode.addStringProperty("completion_code_id");
        completionCode.addStringProperty("department_id");
        completionCode.addStringProperty("completion_code_name");
        completionCode.addStringProperty("completion_code_percentage");
        
        
    }

}
