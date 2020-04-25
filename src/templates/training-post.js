import React from 'react'
import Layout from '../components/layout'
import Wrapper from '../components/Wrapper'
import Article from '../components/Article'
import { graphql } from 'gatsby';

class TrainingPostTemplate extends React.Component {

  render() {
    const { data: { trainingJson: { message } } } = this.props;
    return (
      <Layout location={this.props.location}>
        <Wrapper>
          <Article post={message} />
        </Wrapper>
      </Layout>
    )
  }
}

export default TrainingPostTemplate;

export const pageQuery = graphql`
  query($id: String!) {
    trainingJson(id: {eq: $id}) {
      message
      id
    }
  }
`;
