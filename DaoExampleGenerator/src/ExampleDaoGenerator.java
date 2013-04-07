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
        Schema schema = new Schema(3, "com.osgo.verifi.entity");

        addAssignment(schema);

        new DaoGenerator().generateAll(schema, "src-gen");
    }

    private static void addAssignment(Schema schema) {
        Entity assignment = schema.addEntity("Assignment");
        assignment.implementsInterface("Serializable");
        assignment.addIdProperty();
        assignment.addStringProperty("assignment_id");
        assignment.addStringProperty("time_slot_start");
        assignment.addStringProperty("time_slot_end");
        assignment.addIntProperty("survey_id");
        assignment.addStringProperty("additional_comments");
        assignment.addStringProperty("key");
        assignment.addBooleanProperty("submitted");
        
        Entity installation = schema.addEntity("Installation");
        installation.implementsInterface("Serializable");
        installation.addIdProperty();
        installation.addStringProperty("firstname");
        installation.addStringProperty("lastname");
        installation.addStringProperty("address_1");
        installation.addStringProperty("address_2");
        installation.addStringProperty("city");
        installation.addStringProperty("country");
        installation.addStringProperty("postcode");
        installation.addStringProperty("telephone");
        installation.addStringProperty("mobile");
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
        survey.implementsInterface("Serializable");
        survey.addStringProperty("survey_title");
        survey.addLongProperty("offset");
        survey.addLongProperty("original");
        survey.addBooleanProperty("inQueue");
        survey.addBooleanProperty("inProgress").index();
        
        Entity photo = schema.addEntity("Photo");
        photo.addIdProperty();
        photo.implementsInterface("Serializable");
        photo.addStringProperty("title");
        photo.addStringProperty("path");
        Property photoToSuvey = photo.addLongProperty("surveyId").getProperty();
        photo.addToOne(survey, photoToSuvey);
        
        ToMany surveyToPhotos = survey.addToMany(photo, photoToSuvey);
        surveyToPhotos.setName("photos");
        
        Entity group = schema.addEntity("Group");
        group.setTableName("GROUPDB");
        group.implementsInterface("Serializable");
        group.addIdProperty();
        group.addStringProperty("group_title");
        Property surveyId = group.addLongProperty("surveyId").getProperty();
        group.addToOne(survey, surveyId);
        
        ToMany surveyToGroups = survey.addToMany(group, surveyId);
        surveyToGroups.setName("groups");
        
        Entity question = schema.addEntity("Question");
        question.implementsInterface("Serializable");
        question.addIdProperty();
        question.addStringProperty("alert_type");
        question.addStringProperty("question");
        question.addStringProperty("answer_type");
        question.addIntProperty("multiple_choice");
        question.addIntProperty("required");
        question.addStringProperty("answer_options");
        question.addIntProperty("requires_photo");
        question.addBooleanProperty("send");
        question.addIntProperty("requires_comments");
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
        surveyResponse.implementsInterface("Serializable");
        surveyResponse.implementsInterface("IResponse");
        surveyResponse.addIdProperty();
        surveyResponse.addStringProperty("alert_type");
        surveyResponse.addStringProperty("assignment_id");
        surveyResponse.addStringProperty("api_key");
        surveyResponse.addStringProperty("survey_id");
        surveyResponse.addStringProperty("offset");
        surveyResponse.addStringProperty("survey_started_datetime");
        surveyResponse.addStringProperty("survey_ended_datetime");
        surveyResponse.addStringProperty("survey_started_gps");
        surveyResponse.addStringProperty("survey_ended_gps");
        surveyResponse.addStringProperty("completion_code_id");
        surveyResponse.addStringProperty("photos");
        surveyResponse.addStringProperty("videos");
        surveyResponse.addStringProperty("queue");
        
        Entity answer = schema.addEntity("Answer");
        answer.implementsInterface("Serializable");
        answer.addIdProperty();
        answer.addStringProperty("question_id");
        answer.addStringProperty("answer");
        answer.addStringProperty("comments");
        Property responseId = answer.addLongProperty("responseId").getProperty();
        answer.addToOne(surveyResponse, responseId);      
        
        ToMany answerToResponse = surveyResponse.addToMany(answer, responseId);
        answerToResponse.setName("survey_answers");
        
        Entity unScheduledSurveyResponse = schema.addEntity("UnScheduledSurveyResponse");
        unScheduledSurveyResponse.implementsInterface("Serializable");
        unScheduledSurveyResponse.implementsInterface("IResponse");
        unScheduledSurveyResponse.addIdProperty();
        unScheduledSurveyResponse.addStringProperty("alert_type");
        unScheduledSurveyResponse.addStringProperty("assignment_id");
        unScheduledSurveyResponse.addStringProperty("api_key");
        unScheduledSurveyResponse.addStringProperty("survey_id");
        unScheduledSurveyResponse.addStringProperty("offset");
        unScheduledSurveyResponse.addStringProperty("survey_started_datetime");
        unScheduledSurveyResponse.addStringProperty("survey_ended_datetime");
        unScheduledSurveyResponse.addStringProperty("survey_started_gps");
        unScheduledSurveyResponse.addStringProperty("survey_ended_gps");
        unScheduledSurveyResponse.addStringProperty("completion_code_id");
        unScheduledSurveyResponse.addStringProperty("photos");
        unScheduledSurveyResponse.addStringProperty("videos");
        unScheduledSurveyResponse.addStringProperty("queue");
        
        unScheduledSurveyResponse.addStringProperty("client");
        unScheduledSurveyResponse.addStringProperty("project");
        unScheduledSurveyResponse.addStringProperty("job_type");
        unScheduledSurveyResponse.addStringProperty("location");
        unScheduledSurveyResponse.addStringProperty("work_id");
        
        Property unresponseId = answer.addLongProperty("unresponseId").getProperty();
        answer.addToOne(unScheduledSurveyResponse, unresponseId);     
        
        ToMany answerToUnResponse = unScheduledSurveyResponse.addToMany(answer, unresponseId);
        answerToUnResponse.setName("survey_answers");
        
        
        Entity groupDB = schema.addEntity("GroupDB");
        groupDB.addIdProperty();
        groupDB.addLongProperty("survey");
        groupDB.addStringProperty("title");
        
        Entity project = schema.addEntity("Project");
        project.implementsInterface("Serializable");
        project.addIdProperty();
        project.addStringProperty("key");
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
        project.addStringProperty("work_ids");
        
        Entity completionCode = schema.addEntity("CompletionCode");
        completionCode.implementsInterface("Serializable");
        completionCode.addIdProperty();
        completionCode.addLongProperty("surveyId");
        completionCode.addStringProperty("completion_code_id");
        completionCode.addStringProperty("department_id");
        completionCode.addStringProperty("completion_code_name");
        completionCode.addStringProperty("completion_code_percentage");
        
        Entity clientItem = schema.addEntity("ClientItem");
        clientItem.implementsInterface("Serializable");
        clientItem.addIdProperty();
	    clientItem.addStringProperty("business_name");
	    clientItem.addStringProperty("project");
	    
	    Entity location = schema.addEntity("LocationItem");
	    location.implementsInterface("Serializable");
	    location.addStringProperty("key");
	    location.addIdProperty();
	    location.addStringProperty("location_id");
	    location.addStringProperty("location_name");
	    
	    Entity activity = schema.addEntity("ActivityItem");
	    activity.implementsInterface("Serializable");
	    activity.addStringProperty("key");
	    activity.addIdProperty();
	    activity.addStringProperty("activity_type_id");
	    activity.addStringProperty("activity_type_name");
	    
	    Entity calendar = schema.addEntity("CalendarEvent");
	    calendar.implementsInterface("Serializable");
	    calendar.addIdProperty();
	    calendar.addStringProperty("event_id");
	    calendar.addStringProperty("event_start");
	    calendar.addStringProperty("event_end");
	    calendar.addStringProperty("event_text");
	    calendar.addStringProperty("event_type");
	    calendar.addStringProperty("event_minutes");
	    calendar.addStringProperty("event_title");
	    
	    Entity event = schema.addEntity("Event");
	    event.implementsInterface("Serializable");
	    event.addIdProperty();
	    event.addStringProperty("q_diary_id");
	    event.addStringProperty("user_id");
	    event.addStringProperty("activity_type_id");
	    event.addStringProperty("project_id");
	    event.addStringProperty("client_id");
	    event.addStringProperty("location_id");
	    event.addStringProperty("q_diary_start");
	    event.addStringProperty("q_diary_start_date");
	    event.addStringProperty("q_diary_end");
	    event.addStringProperty("q_diary_minutes");
	    event.addStringProperty("q_diary_notes");
	    event.addStringProperty("q_diary_expenses");
	    event.addStringProperty("q_diary_gps_lat");
	    event.addStringProperty("q_diary_gps_lon");
	    
	    Entity folder = schema.addEntity("Folder");
	    folder.implementsInterface("Serializable");
	    folder.addIdProperty();
	    folder.addStringProperty("folder_id");
	    folder.addStringProperty("folder_name");
	    folder.addStringProperty("folder_last_modified");
	    folder.addStringProperty("folder_created_date");
	    folder.addStringProperty("created_by_full_name");
	    folder.addStringProperty("modified_by_full_name");
	    

	    Entity folderItem = schema.addEntity("FolderItem");
	    folderItem.implementsInterface("Serializable");
	    folderItem.addIdProperty();
	    folderItem.addStringProperty("folder_id");
	    folderItem.addStringProperty("file_id");
	    folderItem.addStringProperty("folder_name");
	    folderItem.addStringProperty("file_token");
	    folderItem.addStringProperty("file_name");
	    folderItem.addStringProperty("file_size");
	    folderItem.addStringProperty("created_date");
	    folderItem.addStringProperty("file_modified_date");
	    folderItem.addStringProperty("file_created_date");
	    folderItem.addStringProperty("created_by_full_name");
	    folderItem.addStringProperty("modified_by_full_name");
	    folderItem.addStringProperty("link");
	    folderItem.addStringProperty("file_filename");
	    
	    Entity queueItem = schema.addEntity("QueueItem");
	    queueItem.addIdProperty();
	    queueItem.addStringProperty("queueName").index();
	    queueItem.addStringProperty("type").index();
	    queueItem.addLongProperty("itemId").index();
	    
	    Entity mediaQueue = schema.addEntity("Media");
	    mediaQueue.addIdProperty();
	    mediaQueue.addStringProperty("title");
	    mediaQueue.addStringProperty("path");
	    mediaQueue.addLongProperty("survey");
	    mediaQueue.addLongProperty("question");
	    
	    Entity surveyQueue = schema.addEntity("SurveyQueue");
	    surveyQueue.addIdProperty();
	    surveyQueue.addLongProperty("RESPONSE");   	
	    
    }

}
