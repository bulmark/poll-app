import React, { useEffect, useState } from "react";
import { Table } from "antd";
import PollApi from "../../api/PollApi";
import moment from "moment";

const PollTable = ({ columns, getMethod }) => {
  const [polls, setPolls] = useState(null);
  const [paginationConfig, setPaginationConfig] = useState(null);
  const [currentPage, setCurrentPage] = useState(1);
  const pageSize = 10;

  useEffect(() => {
    getMethod(currentPage, pageSize).then((response) => {
      let data = response.content;

      data.map((item, index) => {
        if (item.votingTime) {
          let duration = moment.duration(item.votingTime).asMilliseconds();
          let timeOfEnding = new Date(
            new Date(item.createAt).getTime() + duration
          );
          item.timeOfEnding = timeOfEnding.toLocaleString();
        } else {
          item.timeOfEnding = "-";
        }

        if (item.period) {
          item.period = moment.duration(item.period).asDays();
        } else {
          item.period = "-";
        }
      });

      setPolls(response.content);

      setPaginationConfig({
        pageSize,
        total: response.numberOfElements,
        current: currentPage,
        onChange: handlePageChanged,
      });
    });
  }, [currentPage]);

  const handlePageChanged = (page, pageSize) => {
    setCurrentPage(page);
  };

  return (
    <Table columns={columns} dataSource={polls} pagination={paginationConfig} />
  );
};

export default PollTable;
