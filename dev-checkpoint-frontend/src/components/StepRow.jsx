import React from "react";

function StepRow(props) {

  function handleStatusChange(e) {
    props.onChange({
      ...props.data,
      status: e.target.value || "NA"
    });
  }

  function handleDescChange(e) {
    props.onChange({
      ...props.data,
      description: e.target.value || ""
    });
  }

  return (
    <div className="step-row">
      <span className="step-name">
        {props.data.stepNumber}. {props.data.name}
      </span>

      <select
        value={props.data.status ?? "NA"}
        onChange={handleStatusChange}
      >
        <option value="NA">NA</option>
        <option value="YES">Yes</option>
        <option value="NO">No</option>
      </select>

      <input
        type="text"
        placeholder="Remarks"
        value={props.data.description ?? ""}
        onChange={handleDescChange}
      />
    </div>
  );
}

export default StepRow;