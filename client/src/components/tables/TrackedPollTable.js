import React from "react";
import { Space, Table } from "antd";
import { trackedPollColumns } from "../../constants/Columns";

const columns = trackedPollColumns;

const data = [
  {
    text: "Как правильно почехонить яички",
    timeOfEnding: "десятого мая",
    period: "3 часа",
  },
];

const PollTable = () => <Table columns={columns} dataSource={data} />;
export default PollTable;
