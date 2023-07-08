import React from "react";
import PollTable from "./PollTable";
import { availablePollColumns } from "../../constants/Columns";
import PollApi from "../../api/PollApi";

const AvailablePollTable = () => {
  return (
    <PollTable
      columns={availablePollColumns}
      getMethod={PollApi.getAvailable}
    ></PollTable>
  );
};

export default AvailablePollTable;
