package com.example.pollprojectmain.util;

public class MessageProvider {
    public static String userNotFound(Integer userId) {
        return "No such user with " + userId + " id";
    }

    public static String pollNotFound(Integer pollId) {
        return "No such poll with " + pollId + " id";
    }

    public static  String allowToVoteSuccess() {
        return "New users added to vote";
    }

    public static  String createPollSuccess() {
        return "New poll successfully created";
    }

    public static  String createUserSuccess() {
        return "New user successfully created";
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
        return "Submitted a vote for a question from another poll, or not all questions were voted on";
    }

    public static String userExistsWithEmail(String email) {
        return "Submitted a vote for a question from another poll, or not all questions were voted on";
    }
    public static String userExistsWithUsername(String username) {
        return "Submitted a vote for a question from another poll, or not all questions were voted on";
    }


}
