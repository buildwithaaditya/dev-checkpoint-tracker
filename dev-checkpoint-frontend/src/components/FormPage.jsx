import React, { useState } from "react";
import initialData from "../data/InitialData";
import EnvironmentSection from "./EnvironmentSection";

function FormPage() {
  const [formData, setFormData] = useState(initialData);

  function updateStep(envIndex, stepIndex, updatedStep) {
    const updated = [...formData];
    updated[envIndex].steps[stepIndex] = updatedStep;
    setFormData(updated);
  }

 function handleSubmit() {

  const payload = {
    environments: formData.map(env => ({
      environmentName: env.environmentName,
      steps: env.steps.map(step => {
        const { isTesting, ...cleanStep } = step;
        return cleanStep;
      })
    }))
  };

  console.log("FINAL PAYLOAD:", payload);

  fetch("http://localhost:5000/api/excel/generate", {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(payload)
  })
    .then(res => res.blob())
    .then(blob => {
      const url = window.URL.createObjectURL(blob);
      const a = document.createElement("a");
      a.href = url;
      a.download = "report.xlsx";
      a.click();
    });
}
  return (
    <div className="container">
      <h2>Development Tracker</h2>

      {formData.map((env, envIndex) => (
        <EnvironmentSection
          key={envIndex}
          envIndex={envIndex}
          environment={env.environmentName}
          steps={env.steps}
          updateStep={updateStep}
        />
      ))}

      <button className="submit-btn" onClick={handleSubmit}>
        Generate Excel
      </button>
    </div>
  );
}

export default FormPage;