import React from "react";
import { createdPollColumns } from "../../constants/Columns";
import PollTable from "./PollTable";
import PollApi from "../../api/PollApi";

const CreatedPollTable = () => {
  return (
    <PollTable
      columns={createdPollColumns}
      getMethod={PollApi.getCreated}
    ></PollTable>
  );
};

export default CreatedPollTable;
