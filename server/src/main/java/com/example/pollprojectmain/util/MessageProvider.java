package com.example.pollprojectmain.util;

public class MessageProvider {
    public static String noAccessFor(Integer userId, Integer pollId) {
        return "User with id " + userId + " doesnt have access to poll with id " + pollId;
    }
    public static String userNotFound(Integer userId) {
        return "No such user with " + userId + " id";
    }

    public static String pollNotFound(Integer pollId) {
        return "No such poll with " + pollId + " id";
    }

    public static  String allowToVoteSuccess() {
        return "New users added to vote";
    }

    public static  String createPollSuccess(Integer pollId) {
        return "New poll successfully created with id " + pollId;
    }

    public static  String createUserSuccess(Integer userId) {
        return "New user with id " + userId + "  successfully created";
    }

    public static  String changePasswordSuccess(Integer userId) {
        return "User with id " + userId + " successfully updated his password";
    }

    public  static String updateUserSuccess(Integer userId) {
        return "User with id " + userId + " successfully updated";
    }

    public static  String pollIsOver(Integer pollId) {
        return "Poll with id " + pollId + " is over";
    }

    public static  String userVoteSuccess(Integer userId) {
        return "User with id " + userId + " has successfully voted";
    }

    public static String questionSkipped(Integer questionId, String questionText) {
        return "You didn't vote in the question with id " + questionId +  " and text:\n" +
                questionText;
    }

    public static String toMuchVotes(Integer questionId, String questionText) {
        return "To much votes in the question with id " + questionId +  " and text:\n" +
                questionText;
    }

    public static String userHasAlreadyVoted(Integer userId) {
        return "User with id " + userId + " has already voted";
    }

    public static String incorrectListOfQuestionsAnswered() {
        return "The given vote list of answered questions is incorrect ";
    }

    public static String userExistsWithEmail(String email) {
        return "User with such email already exists";
    }
    public static String userExistsWithUsername(String username) {
        return "User with such username already exists";
    }

    public static String userInNotOwner(Integer id) {
        return "User with " + id + " doesnt own this poll";
    }


}
