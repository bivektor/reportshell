## Setting Default Values for Input Controls

## Overview

This sample demonstrates how to set default values for input controls on initial loading of control
states so that the UI can display the default value.

## Default Value Evaluation Events 

This sample implements a `ControlDefaultValueListener` which is
triggered before and after the parameter default value expressions are evaluated.

This happens only when initially loading the control states via the input-controls REST API.

The component overrides the default value expression for the `createdAfter` control. 
Because it sets a value for the same parameter, the framework 
does not evaluate the parameter's default value expression.

## Running the Sample

> **Important**
> Before running this sample, make sure you've read the instructions on running the samples in the
> repository [README](../README.md)

1. Start the application:
   From the `samples/projects` directory
   ```bash
   ./gradlew :control-defaults:bootRun
   ```

2. Open your browser to `http://localhost:8080`
3. Navigate to the **Customers Report**
4. The report viewer shows the custom default value for the `createdAfter` input control.

