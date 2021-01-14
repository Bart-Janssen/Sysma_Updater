package sysmaUpdater.controllers;

public interface IUpdateController
{
    void performStep();
    void setVersion(String version);
}