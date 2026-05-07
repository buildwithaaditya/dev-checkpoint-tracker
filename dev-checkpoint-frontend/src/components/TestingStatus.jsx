import React from "react";

function TestingStatus(props) {

  function handleChange(type, field, value) {
    props.onChange({
      ...props.data,
      testingStatus: {
        ...props.data.testingStatus,
        [type]: {
          ...props.data.testingStatus?.[type],
          [field]: value
        }
      }
    });
  }

  function renderRow(label, key) {
    const row = props.data.testingStatus?.[key] || {}; // ✅ FIX

    return (
      <div style={{ marginBottom: "8px" }}>
        <b>{label}</b>

        <select
          value={row.status || "NA"}
          onChange={(e) => handleChange(key, "status", e.target.value)}
        >
          <option value="NA">NA</option>
          <option value="YES">Yes</option>
          <option value="NO">No</option>
        </select>

        <input
          type="text"
          placeholder="Remarks"
          value={row.description || ""}
          onChange={(e) => handleChange(key, "description", e.target.value)}
        />
      </div>
    );
  }

  return (
    <div style={{ margin: "10px 0", paddingLeft: "20px" }}>
      <h4>Testing Status</h4>
      {renderRow("Success", "success")}
      {renderRow("Fault", "fault")}
      {renderRow("Exception", "exception")}
    </div>
  );
}

export default TestingStatus;