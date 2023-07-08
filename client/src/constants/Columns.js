import { Space } from "antd";
import React from "react";

export const actions = {
  result: "result",
  invite: "invite",
  vote: "vote",
};
export const pollColumns = [
  { title: "Poll topic", dataIndex: "text", key: "text" },
  { title: "Time of ending", dataIndex: "timeOfEnding", key: "timeOfEnding" },
  { title: "Period in days", dataIndex: "period", key: "period" },
];

export const availablePollColumns = [
  ...pollColumns,
  {
    title: "Action",
    key: "action",
    render: (_, record) => (
      <Space size="middle">
        <a>{actions.vote}</a>
      </Space>
    ),
  },
];

export const createdPollColumns = [
  ...pollColumns,
  {
    title: "Action",
    key: "action",
    render: (_, record) => (
      <Space size="middle">
        <a>{actions.invite}</a>
        <a>{actions.result}</a>
      </Space>
    ),
  },
];

export const trackedPollColumns = [
  ...pollColumns,
  {
    title: "Action",
    key: "action",
    render: (_, record) => (
      <Space size="middle">
        <a>{actions.result}</a>
      </Space>
    ),
  },
];
