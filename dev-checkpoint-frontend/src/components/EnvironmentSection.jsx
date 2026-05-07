import React from "react";
import StepRow from "./StepRow";
import TestingStatus from "./TestingStatus";

function EnvironmentSection(props) {
  return (
    <div className="env-box">
      <h3>{props.environment}</h3>

      {props.steps.map((step, index) => (
        <div key={step.stepNumber}>
          {step.isTesting ? (
            <>
              {/* 🔥 Main Step Row */}
              <div className="step-row">
                <span className="step-name">
                  {step.stepNumber}. {step.name}
                </span>
              </div>

              {/* 🔽 Sub rows */}
              <div className="testing-wrapper">
                <TestingStatus
                  data={step}
                  onChange={(updated) =>
                    props.updateStep(props.envIndex, index, updated)
                  }
                />
              </div>
            </>
          ) : (
            <StepRow
              data={step}
              onChange={(updated) =>
                props.updateStep(props.envIndex, index, updated)
              }
            />
          )}
        </div>
      ))}
    </div>
  );
}

export default EnvironmentSection;