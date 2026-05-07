import defaultStep from "./defaultStep";

 const initialData = [
  {
    environmentName: "Appworks Development",
    steps: [
      defaultStep(1, "Jar Configuration"),
      defaultStep(2, "Samples required for testing"),
      defaultStep(3, "Role Attached in service"),

      {
        stepNumber: 4,
        name: "Testing Status",
        isTesting: true,
        testingStatus: {
          success: { status: "NA", description: "" },
          fault: { status: "NA", description: "" },
          exception: { status: "NA", description: "" }
        }
      },

      defaultStep(5, "Docs shared"),
      defaultStep(6, "Package Created"),
      defaultStep(7, "Workspace commited"),
      defaultStep(8, "Soapui Project SVN committed"),
      defaultStep(9, "SOAPUI response validation"),
      defaultStep(10, "Service Added in dashboard"),
      defaultStep(11, "Alf Logging Status")
    ]
  },
  {
    environmentName: "FAT Appworks Deployment",
    steps: [
      defaultStep(1, "Package Deployed"),
      defaultStep(2, "configs done"),

      {
        stepNumber: 3,
        name: "Testing Status",
        isTesting: true,
        testingStatus: {
          success: { status: "NA", description: "" },
          fault: { status: "NA", description: "" },
          exception: { status: "NA", description: "" }
        }
      }
    ]
  }
];


export default initialData;