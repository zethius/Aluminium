package com.zespolowka.entity.solutionTest.config;

import org.json.simple.JSONObject;

import java.util.HashMap;

/**
 * Created by Admin on 2016-04-17.
 */
public class SolutionConfig {
    public static final String MAIN_FILE = "main_file";
    public static final String RESTRICTED_LIST_PATH = "restricted_list_path";
    public static final String COMPILER = "compiler";
    public static final String INTERPRETER = "interpreter";
    public static final String COMPILER_FLAGS = "compiler_flags";
    public static final String INTERPRETER_FLAGS = "interpreter_flags";
    public static final String ULIMIT_FLAGS = "ulimit_flags";
    public static final String MAX_TIME_OF_TESTS = "max_time_of_tests";
    public static final String LANGUAGE = "language";
    public static final String SOURCES = "sources";
    public static final String TESTER_FILES = "tester_files";
    public static final String PREPARATIONS = "preparations";
    public static final String TESTS = "tests";

    private String mainFile;
    private String restricted_list_path;
    private String compiler;
    private String interpreter;
    private String compiler_flags;
    private String interpreter_flags;
    private String ulimit_flags = "-t 20";
    private Integer max_time_of_tests = 5;
    private String language;
    private String sources;
    private String tester_files;
    private String preparations;
    private String tests;

    public SolutionConfig() {
    }

    public SolutionConfig(String mainFile, String restricted_list_path, String compiler, String interpreter, String compiler_flags, String interpreter_flags, String ulimit_flags, Integer max_time_of_tests, String language, String sources, String tester_files, String preparations, String tests) {
        this.mainFile = mainFile;
        this.restricted_list_path = restricted_list_path;
        this.compiler = compiler;
        this.interpreter = interpreter;
        this.compiler_flags = compiler_flags;
        this.interpreter_flags = interpreter_flags;
        this.ulimit_flags = ulimit_flags;
        this.max_time_of_tests = max_time_of_tests;
        this.language = language;
        this.sources = sources;
        this.tester_files = tester_files;
        this.preparations = preparations;
        this.tests = tests;
    }


    public JSONObject createJavaConfig(String sources, String tests, String restricted_list_path) {
        this.language = "java";
        this.sources = sources;
        this.tests = tests;
        this.compiler = "/usr/bin/javac";
        this.interpreter = "/usr/bin/java";
        this.compiler_flags = "-cp .:/home/pitek/zespolowka/skrypty/JAVA_LIB/junit-4.12.jar";
        this.interpreter_flags = "-cp .:/home/pitek/zespolowka/skrypty/JAVA_LIB/*";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(LANGUAGE, language);
        jsonObject.put(SOURCES, sources);
        HashMap<String, String> map = new HashMap<>();
        map.put(TESTS, tests);
        map.put(PREPARATIONS, preparations);
        jsonObject.put(TESTER_FILES, map);
        jsonObject.put(MAIN_FILE, mainFile);
        jsonObject.put(RESTRICTED_LIST_PATH, restricted_list_path);
        jsonObject.put(COMPILER, compiler);
        jsonObject.put(INTERPRETER, interpreter);
        jsonObject.put(COMPILER_FLAGS, compiler_flags);
        jsonObject.put(INTERPRETER_FLAGS, interpreter_flags);
        jsonObject.put(ULIMIT_FLAGS, ulimit_flags);
        jsonObject.put(MAX_TIME_OF_TESTS, max_time_of_tests);
        return jsonObject;
    }

    public JSONObject createCppConfig(String sources, String tests, String restricted_list_path, String compiler_flags) {
        this.language = "c++";
        this.sources = sources;
        this.tests = tests;
        this.compiler = "/usr/bin/c++";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(LANGUAGE, language);
        jsonObject.put(SOURCES, sources);
        HashMap<String, String> map = new HashMap<>();
        map.put(TESTS, tests);
        map.put(PREPARATIONS, preparations);
        jsonObject.put(TESTER_FILES, map);
        jsonObject.put(MAIN_FILE, mainFile);
        jsonObject.put(RESTRICTED_LIST_PATH, restricted_list_path);
        jsonObject.put(COMPILER, compiler);
        jsonObject.put(INTERPRETER, interpreter);
        jsonObject.put(COMPILER_FLAGS, compiler_flags);
        jsonObject.put(INTERPRETER_FLAGS, interpreter_flags);
        jsonObject.put(ULIMIT_FLAGS, ulimit_flags);
        jsonObject.put(MAX_TIME_OF_TESTS, max_time_of_tests);
        return jsonObject;
    }

    public JSONObject createPythonConfig(String sources, String tests, String restricted_list_path) {
        this.language = "python";
        this.sources = sources;
        this.tests = tests;
        this.compiler = "/usr/bin/jython";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(LANGUAGE, language);
        jsonObject.put(SOURCES, sources);
        HashMap<String, String> map = new HashMap<>();
        map.put(TESTS, tests);
        map.put(PREPARATIONS, preparations);
        jsonObject.put(TESTER_FILES, map);
        jsonObject.put(MAIN_FILE, mainFile);
        jsonObject.put(RESTRICTED_LIST_PATH, restricted_list_path);
        jsonObject.put(COMPILER, compiler);
        jsonObject.put(INTERPRETER, interpreter);
        jsonObject.put(COMPILER_FLAGS, compiler_flags);
        jsonObject.put(INTERPRETER_FLAGS, interpreter_flags);
        jsonObject.put(ULIMIT_FLAGS, ulimit_flags);
        jsonObject.put(MAX_TIME_OF_TESTS, max_time_of_tests);
        return jsonObject;
    }

    public JSONObject createSqlConfig(String sources, String preparations, String mainFile, String tests, String restricted_list_path) {
        this.language = "sql";
        this.sources = sources;
        this.tests = tests;
        this.interpreter = "/usr/bin/python3";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(LANGUAGE, language);
        jsonObject.put(SOURCES, sources);
        HashMap<String, String> map = new HashMap<>();
        map.put(TESTS, tests);
        map.put(PREPARATIONS, preparations);
        jsonObject.put(TESTER_FILES, map);
        jsonObject.put(MAIN_FILE, mainFile);
        jsonObject.put(RESTRICTED_LIST_PATH, restricted_list_path);
        jsonObject.put(COMPILER, compiler);
        jsonObject.put(INTERPRETER, interpreter);
        jsonObject.put(COMPILER_FLAGS, compiler_flags);
        jsonObject.put(INTERPRETER_FLAGS, interpreter_flags);
        jsonObject.put(ULIMIT_FLAGS, ulimit_flags);
        jsonObject.put(MAX_TIME_OF_TESTS, max_time_of_tests);
        return jsonObject;
    }


    public String getMainFile() {
        return mainFile;
    }

    public void setMainFile(String mainFile) {
        this.mainFile = mainFile;
    }


    public String getRestricted_list_path() {
        return restricted_list_path;
    }

    public void setRestricted_list_path(String restricted_list_path) {
        this.restricted_list_path = restricted_list_path;
    }

    public String getCompiler() {
        return compiler;
    }

    public void setCompiler(String compiler) {
        this.compiler = compiler;
    }

    public String getInterpreter() {
        return interpreter;
    }

    public void setInterpreter(String interpreter) {
        this.interpreter = interpreter;
    }

    public String getCompiler_flags() {
        return compiler_flags;
    }

    public void setCompiler_flags(String compiler_flags) {
        this.compiler_flags = compiler_flags;
    }

    public String getInterpreter_flags() {
        return interpreter_flags;
    }

    public void setInterpreter_flags(String interpreter_flags) {
        this.interpreter_flags = interpreter_flags;
    }

    public String getUlimit_flags() {
        return ulimit_flags;
    }

    public void setUlimit_flags(String ulimit_flags) {
        this.ulimit_flags = ulimit_flags;
    }

    public Integer getMax_time_of_tests() {
        return max_time_of_tests;
    }

    public void setMax_time_of_tests(Integer max_time_of_tests) {
        this.max_time_of_tests = max_time_of_tests;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSources() {
        return sources;
    }

    public void setSources(String sources) {
        this.sources = sources;
    }

    public String getTester_files() {
        return tester_files;
    }

    public void setTester_files(String tester_files) {
        this.tester_files = tester_files;
    }

    public String getPreparations() {
        return preparations;
    }

    public void setPreparations(String preparations) {
        this.preparations = preparations;
    }

    public String getTests() {
        return tests;
    }

    public void setTests(String tests) {
        this.tests = tests;
    }

    @Override
    public String toString() {
        return "SolutionConfig{" +
                "mainFile='" + mainFile + '\'' +
                ", restricted_list_path='" + restricted_list_path + '\'' +
                ", compiler='" + compiler + '\'' +
                ", interpreter='" + interpreter + '\'' +
                ", compiler_flags='" + compiler_flags + '\'' +
                ", interpreter_flags='" + interpreter_flags + '\'' +
                ", ulimit_flags='" + ulimit_flags + '\'' +
                ", max_time_of_tests='" + max_time_of_tests + '\'' +
                ", language='" + language + '\'' +
                ", sources='" + sources + '\'' +
                ", tester_files='" + tester_files + '\'' +
                ", preparations='" + preparations + '\'' +
                ", tests='" + tests + '\'' +
                '}';
    }
}
